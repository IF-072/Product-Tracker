package com.softserve.if072.mvcapp.dto;

import java.util.List;

public class StoresInProduct {

    private List<Integer> storesId;

    public List<Integer> getStoresId() {
        return storesId;
    }

    public void setStoresId(List<Integer> storesId) {
        this.storesId = storesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StoresInProduct that = (StoresInProduct) o;

        return storesId != null ? storesId.equals(that.storesId) : that.storesId == null;
    }

    @Override
    public int hashCode() {
        return storesId != null ? storesId.hashCode() : 0;
    }
}
