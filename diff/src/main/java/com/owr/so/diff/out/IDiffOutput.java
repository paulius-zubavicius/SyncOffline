package com.owr.so.diff.out;

import java.util.List;

import com.owr.so.diff.model.RepoDiff;
import com.owr.so.diff.model.ReposRootPaths;

public interface  IDiffOutput {

	<T extends RepoDiff> void  out(Class<T> type, List<T> data, ReposRootPaths rootPathByRepoName);


}
