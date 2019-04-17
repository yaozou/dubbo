package com.yao.server.rpc;

import com.yao.server.bean.RpcRequest;

import java.lang.reflect.Method;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description:
 * @author: yaozou
 * @Date: 2019/4/17 15:08
 */
public class RpcServerHandler extends ChannelInboundHandlerAdapter {
    private Map<String, Object> handlerMap;
    public RpcServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RpcRequest request = (RpcRequest) msg;
        Object result = new Object();
        if (handlerMap.containsKey(request.getClassName())){
            // 调用map中的之类对象进行执行
           Object clazz =  handlerMap.get(request.getClassName());
           Method method = clazz.getClass().getMethod(request.getMethodName(),request.getTypes());
           method.invoke(clazz,request.getParams());
        }
        ctx.write(result);
        ctx.flush();
        ctx.close();
    }
}
