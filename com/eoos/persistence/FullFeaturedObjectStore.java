package com.eoos.persistence;

import com.eoos.persistence.mixin.BulkLoad;
import com.eoos.persistence.mixin.BulkStore;
import com.eoos.persistence.mixin.Content;
import com.eoos.persistence.mixin.Existence;

public interface FullFeaturedObjectStore extends ObjectStore, BulkLoad, BulkStore, Content, Existence {}


/* Location:              D:\tech\G TIS\tis2web.jar!\com\eoos\persistence\FullFeaturedObjectStore.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */