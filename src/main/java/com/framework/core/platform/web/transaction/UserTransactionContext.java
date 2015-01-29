package com.framework.core.platform.web.transaction;

import com.framework.core.collection.Key;
import com.framework.core.collection.KeyMap;
import com.framework.core.utils.ReadOnly;

public class UserTransactionContext {
    
    private final ReadOnly<String> id = new ReadOnly<String>();
    
    private final KeyMap transaction = new KeyMap();
    
    private boolean changed;
    
    private boolean invalidated;

    public <T> T get(Key<T> key) {
        return transaction.get(key);
    }

    public <T> void set(Key<T> key, T value) {
        transaction.put(key, value);
        changed = true;
    }

    public void invalidate() {
        transaction.clear();
        changed = true;
        invalidated = true;
    }

    boolean changed() {
        return changed;
    }

    boolean invalidated() {
        return invalidated;
    }

    String getId() {
        return id.value();
    }

    void setId(String id) {
        this.id.set(id);
    }

    void loadTransactionData(String transactionData) {
        transaction.deserialize(transactionData);
    }

    String getTransactionData() {
        return transaction.serialize();
    }

    void saved() {
        changed = false;
    }

    void requireNewTransactionId() {
        changed = true;
    }
    
}