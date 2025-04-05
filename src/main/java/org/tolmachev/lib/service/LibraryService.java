package org.tolmachev.lib.service;

import org.tolmachev.lib.model.Subscription;
import org.tolmachev.lib.model.UploadRequest;

public interface LibraryService {
    Subscription getSubscription(String userFullName);
    void saveOldData(UploadRequest uploadRequest);
}
