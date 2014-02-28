package org.openepics.model.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.Lattice;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(MachineMode.class)
public class MachineMode_ { 

    public static volatile SingularAttribute<MachineMode, String> machineModeName;
    public static volatile CollectionAttribute<MachineMode, Lattice> latticeCollection;
    public static volatile SingularAttribute<MachineMode, String> machineModeDescription;
    public static volatile SingularAttribute<MachineMode, Integer> machineModeId;

}