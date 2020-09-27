package org.simple.net.request.body;

/**
 * org.simple.net.request.body
 *
 * @author Simple
 * @date 2020/9/27
 * @desc
 * 表单类型的body体
 */
public class FormBody extends RequestBody{



    @Override
    public BodyType type() {
        return BodyType.TYPE_FORM;
    }

}
