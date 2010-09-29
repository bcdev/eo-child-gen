package com.bc.childgen.modules;

import com.bc.childgen.ChildGenException;
import com.bc.childgen.DatasetDescriptor;

import javax.imageio.stream.ImageInputStream;
import java.io.IOException;


public interface Module {

    public Roi adjustRoi(Roi roi, Sph sph) throws ChildGenException;

    public MdsrLineMap createLineMap(DatasetDescriptor[] dsds, ImageInputStream in) throws IOException, ChildGenException;
}
