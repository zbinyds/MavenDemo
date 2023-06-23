package com.zbinyds.asnyc.utils;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 批量异步支持
 *
 * @author wangxinxing
 * @date 2021/7/9
 */

@Slf4j
public class BatchFutureSupport {
    private static final ThreadPoolExecutor DEFAULT_EXECUTE;

    static {
        DEFAULT_EXECUTE = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    /**
     * 接口为非future，返回值为Map<K, T>类型的
     */
    @SafeVarargs
    public static <K, T> BatchFuture<Map<K, T>> generalBatchFuture(@NotNull final List<K> ids, final int batch,
                                                                   @NotNull final Function<List<K>, Map<K, T>> loadFunction,
                                                                   Predicate<? super T>... predicates) {
        return generalBatchFuture(DEFAULT_EXECUTE, ids, batch, loadFunction, predicates);
    }

    /**
     * 接口为非future，返回值为Map<K, T>类型的
     */
    @SafeVarargs
    public static <K, T> BatchFuture<Map<K, T>> generalBatchFuture(@NotNull final Executor executor,
                                                                   @NotNull final List<K> ids, final int batch,
                                                                   @NotNull final Function<List<K>, Map<K, T>> loadFunction,
                                                                   Predicate<? super T>... predicates) {
        BatchFuture<Map<K, T>> returnFuture = new BatchFuture<>(5000 * ids.size());
        Map<K, T> map = new ConcurrentHashMap<>();
        List<List<K>> subList = partition(ids, batch);
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        subList.forEach(sub -> {
            CompletableFuture<Void> future = CompletableFuture.supplyAsync(() ->
                    loadFunction.apply(sub), executor
            ).thenAccept(map::putAll);
            futureList.add(future);
        });
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[subList.size()])).whenCompleteAsync((result, ex) -> {
            if (ex != null) {
                log.error("generalBatchFuture error,but do not care,please retry");
            }
            Map<K, T> finalMap = map;
            for (Predicate<? super T> predicate : predicates) {
                finalMap = finalMap.entrySet().stream().filter(it -> predicate.test(it.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }
            returnFuture.finish(finalMap);
        });
        return returnFuture;
    }

    /**
     * 接口为future，返回值为Map<K, T>类型的
     */
    @SafeVarargs
    public static <K, T> BatchFuture<Map<K, T>> futureBatchFuture(@NotNull final List<K> ids, final int batch,
                                                                  @NotNull final Function<List<K>, Future<Map<K, T>>> loadFunction,
                                                                  Predicate<? super T>... predicates) {
        return futureBatchFuture(DEFAULT_EXECUTE, ids, batch, loadFunction, predicates);
    }

    /**
     * 接口为future，返回值为Map<K, T>类型的
     */
    @SafeVarargs
    public static <K, T> BatchFuture<Map<K, T>> futureBatchFuture(@NotNull final Executor executor,
                                                                  @NotNull final List<K> ids, final int batch,
                                                                  @NotNull final Function<List<K>, Future<Map<K, T>>> loadFunction,
                                                                  Predicate<? super T>... predicates) {
        BatchFuture<Map<K, T>> returnFuture = new BatchFuture<>(5000 * ids.size());
        Map<K, T> map = new ConcurrentHashMap<>();
        List<List<K>> subList = partition(ids, batch);
        CountDownLatch latch = new CountDownLatch(subList.size());
        subList.forEach(sub -> {
            executor.execute(() -> {
                try {
                    map.putAll(loadFunction.apply(sub).get());
                } catch (Exception ex) {
                    log.error("generalBatchFuture error,but do not care,please retry");
                } finally {
                    latch.countDown();
                }
            });
        });

        executor.execute(() -> {
            try {
                latch.await();
                Map<K, T> finalMap = map;
                for (Predicate<? super T> predicate : predicates) {
                    finalMap = finalMap.entrySet().stream().filter(it -> predicate.test(it.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                }
                returnFuture.finish(map);
            } catch (InterruptedException e) {
                log.info("getTeacherInfoWorker failed");
            }
        });
        return returnFuture;
    }

    /**
     * 接口为非future，返回值为Map<K,Collection<T>>类型的,且有通过谓词过滤需要
     */
    @SafeVarargs
    public static <K, T> BatchFuture<Collection<T>> generalBatchFutureCollection(@NotNull final List<K> ids, final int batch,
                                                                                 @NotNull final Function<List<K>, Map<K, Collection<T>>> loadFunction,
                                                                                 Predicate<? super T>... predicates) {
        return generalBatchFutureCollection(DEFAULT_EXECUTE, ids, batch, loadFunction, predicates);

    }

    /**
     * 接口为非future，返回值为Map<K,Collection<T>>类型的,且有通过谓词过滤需要
     */
    @SafeVarargs
    public static <K, T> BatchFuture<Collection<T>> generalBatchFutureCollection(@NotNull final Executor executor,
                                                                                 @NotNull final List<K> ids, final int batch,
                                                                                 @NotNull final Function<List<K>, Map<K, Collection<T>>> loadFunction,
                                                                                 Predicate<? super T>... predicates) {
        BatchFuture<Collection<T>> returnFuture = new BatchFuture<>(5000 * ids.size());
        Map<K, Collection<T>> map = new ConcurrentHashMap<>();
        List<List<K>> subList = partition(ids, batch);
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        subList.forEach(sub -> {
            CompletableFuture<Void> future = CompletableFuture.supplyAsync(() ->
                    loadFunction.apply(sub), executor
            ).thenAccept(map::putAll);
            futureList.add(future);
        });
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[subList.size()])).whenCompleteAsync((rst, ex) -> {
            if (ex != null) {
                log.error("generalBatchFuture error,but do not care,please retry");
            }
            Collection<T> result = map.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
            for (Predicate<? super T> predicate : predicates) {
                result = result.stream().filter(predicate).collect(Collectors.toList());
            }
            returnFuture.finish(result);
        });
        return returnFuture;
    }

    /**
     * 接口为future，返回值为Map<K,Collection<T>>类型的,且有通过谓词过滤需要
     */
    @SafeVarargs
    public static <K, T> BatchFuture<Collection<T>> futureBatchFutureCollection(@NotNull final List<K> ids, final int batch,
                                                                                @NotNull final Function<List<K>, Future<Map<K, List<T>>>> loadFunction,
                                                                                Predicate<? super T>... predicates) {
        return futureBatchFutureCollection(DEFAULT_EXECUTE, ids, batch, loadFunction, predicates);
    }

    /**
     * 接口为future，返回值为Map<K,Collection<T>>类型的,且有通过谓词过滤需要
     */
    @SafeVarargs
    public static <K, T> BatchFuture<Collection<T>> futureBatchFutureCollection(@NotNull final Executor executor,
                                                                                @NotNull final List<K> ids, final int batch,
                                                                                @NotNull final Function<List<K>, Future<Map<K, List<T>>>> loadFunction,
                                                                                Predicate<? super T>... predicates) {
        BatchFuture<Collection<T>> returnFuture = new BatchFuture<>(5000 * ids.size());
        Map<K, Collection<T>> map = new ConcurrentHashMap<>();
        List<List<K>> subList = partition(ids, batch);
        CountDownLatch latch = new CountDownLatch(subList.size());
        subList.forEach(sub -> {
            executor.execute(() -> {
                try {
                    map.putAll(loadFunction.apply(sub).get());
                } catch (Exception ex) {
                    log.error("generalBatchFuture error,but do not care,please retry");
                } finally {
                    latch.countDown();
                }
            });
        });

        executor.execute(() -> {
            try {
                latch.await();
                Collection<T> result = map.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
                for (Predicate<? super T> predicate : predicates) {
                    result = result.stream().filter(predicate).collect(Collectors.toList());
                }
                returnFuture.finish(result);
            } catch (InterruptedException e) {
                log.info("getTeacherInfoWorker failed");
            }
        });
        return returnFuture;
    }

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     *
     * @param source
     * @return
     */
    public static <T> List<List<T>> partition(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        int remaider = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remaider > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }


}
