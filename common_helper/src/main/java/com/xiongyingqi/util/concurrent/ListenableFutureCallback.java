/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiongyingqi.util.concurrent;

/**
 * Defines the contract for callbacks that accept the result of a
 * {@link ListenableFuture}.
 *
 * @author Arjen Poutsma
 * @since 4.0
 */
public interface ListenableFutureCallback<T> {

    /**
     * Called when the {@link ListenableFuture} successfully completes.
     *
     * @param result the result
     */
    void onSuccess(T result);

    /**
     * Called when the {@link ListenableFuture} fails to complete.
     *
     * @param t the exception that triggered the failure
     */
    void onFailure(Throwable t);

}
