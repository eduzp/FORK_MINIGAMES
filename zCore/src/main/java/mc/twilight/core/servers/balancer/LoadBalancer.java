package mc.twilight.core.servers.balancer;

import mc.twilight.core.servers.balancer.elements.LoadBalancerObject;

public interface LoadBalancer<T extends LoadBalancerObject> {
  T next();
}
