package com.owr.so.merge.log;

import com.owr.so.model.DirTreeEntity;

public interface IMergeLogEventsListener {

	void dataRead(DirTreeEntity tree1, DirTreeEntity tree2, String metaFile1Path, String metaFile2Path);
}
