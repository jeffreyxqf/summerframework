/**
 * Copyright (C) 2010-2013 Alibaba Group Holding Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.bkjk.platform.dts.remoting;

import java.util.concurrent.ExecutorService;

import com.bkjk.platform.dts.remoting.exception.RemotingSendRequestException;
import com.bkjk.platform.dts.remoting.exception.RemotingTimeoutException;
import com.bkjk.platform.dts.remoting.exception.RemotingTooMuchRequestException;
import com.bkjk.platform.dts.remoting.netty.NettyRequestProcessor;
import com.bkjk.platform.dts.remoting.protocol.RemotingCommand;

import io.netty.channel.Channel;

/**
 * 远程通信，Server接口
 * 
 * @author shijia.wxr<vintage.wang@gmail.com>
 * @since 2013-7-13
 */
public interface RemotingServer extends RemotingService {

    public void invokeAsync(final Channel channel, final RemotingCommand request, final long timeoutMillis,
        final InvokeCallback invokeCallback) throws InterruptedException, RemotingTooMuchRequestException,
        RemotingTimeoutException, RemotingSendRequestException;

    public void invokeOneway(final Channel channel, final RemotingCommand request, final long timeoutMillis)
        throws InterruptedException, RemotingTooMuchRequestException, RemotingTimeoutException,
        RemotingSendRequestException;

    public RemotingCommand invokeSync(final Channel channel, final RemotingCommand request, final long timeoutMillis)
        throws InterruptedException, RemotingSendRequestException, RemotingTimeoutException;

    /**
     * 服务器绑定的本地端口
     * 
     * @return PORT
     */
    public int localListenPort();

    public void registerDefaultProcessor(final NettyRequestProcessor processor, final ExecutorService executor);

    /**
     * 注册请求处理器，ExecutorService必须要对应一个队列大小有限制的阻塞队列，防止OOM
     * 
     * @param requestCode
     * @param processor
     * @param executor
     */
    public void registerProcessor(final int requestCode, final NettyRequestProcessor processor,
        final ExecutorService executor);

}
