package org.tolmachev.lib.service;

import org.tolmachev.lib.model.Data;

import java.util.List;

public interface SubscriptionService {
    void saveSubscriptions(List<Data> dataList);
}
