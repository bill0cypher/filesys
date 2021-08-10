package repository.common;

import java.util.Map;

public interface FormCompleted<T> {
    T fillFormCompleted(T t, Map<String, String> vals);
}
