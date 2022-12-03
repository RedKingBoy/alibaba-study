package com.wfh.shopping.gateway.rule;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateBasedRule;
//基于条件的负载均衡规则
public class GrayReleaseRule extends PredicateBasedRule {
    private AbstractServerPredicate predicate;

    public GrayReleaseRule() {
    }

    public GrayReleaseRule(AbstractServerPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public AbstractServerPredicate getPredicate() {
        return predicate;
    }
}
