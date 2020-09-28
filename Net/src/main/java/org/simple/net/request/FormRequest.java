package org.simple.net.request;

import org.simple.net.request.body.FormBody;

/**
 * org.simple.net.request
 *
 * @author Simple
 * @date 2020/9/27
 * @desc
 */
public class FormRequest extends BodyRequest<FormBody,FormRequest>{

    public FormRequest() {
        body = new FormBody();
    }

}
