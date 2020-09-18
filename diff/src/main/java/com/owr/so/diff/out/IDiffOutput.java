package com.owr.so.diff.out;

import java.util.List;
import java.util.Map;

import com.owr.so.diff.model.RepoDiff;

public interface IDiffOutput {

	<T extends RepoDiff> void out(Class<T> type, List<T> data, Map<String, String> rootPathByRepoName);

}
