package models;

import java.util.List;

/**
 * Created by dastko on 9/7/15.
 */
public interface Page<T> {

    List<T> getList();

    int getTotalRowCount();

    int getTotalPageCount();

    int getPageIndex();

    boolean hasNext();

    boolean hasPrev();

    Page<T> next();

    Page<T> prev();

    String getDisplayXtoYofZ(String var1, String var2);

}
