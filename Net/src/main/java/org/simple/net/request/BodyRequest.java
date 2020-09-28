package org.simple.net.request;

import org.simple.net.request.body.RequestBody;

/**
 * org.simple.net.request
 *
 * @author Simple
 * @date 2020/9/27
 * @descB
 */
public class BodyRequest<B extends RequestBody, R extends BodyRequest<B, R>> extends Request<R> {

    /**
     * body体
     */
    protected B body;

    public B getBody() {
        return body;
    }

    public R setBody(B body) {
        this.body = body;
        return (R) this;
    }


}
