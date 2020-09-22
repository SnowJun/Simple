package org.simple.net.center;

import org.simple.net.request.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * org.simple.net.center
 *
 * @author Simple
 * @date 2020/9/11
 * @desc
 * 网络任务 中心
 */
public class NetCenter {

    private List<Request> runningRequests;

    public NetCenter() {
       runningRequests = new ArrayList<>();
    }

    public List<Request> getRunningRequests() {
        return runningRequests;
    }

    public void setRunningRequests(List<Request> runningRequests) {
        this.runningRequests = runningRequests;
    }

    public void addRunningRequests(Request request){
        if (null != request){
            runningRequests.add(request);
        }
    }

}
