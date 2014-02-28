package org.openepics.model.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.BlsequenceLattice;
import org.openepics.model.entity.ElementProp;
import org.openepics.model.entity.GoldLattice;
import org.openepics.model.entity.MachineMode;
import org.openepics.model.entity.Model;
import org.openepics.model.entity.ModelGeometry;
import org.openepics.model.entity.ModelLine;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(Lattice.class)
public class Lattice_ { 

    public static volatile SingularAttribute<Lattice, String> latticeDescription;
    public static volatile SingularAttribute<Lattice, ModelLine> modelLineId;
    public static volatile SingularAttribute<Lattice, ModelGeometry> modelGeometryId;
    public static volatile CollectionAttribute<Lattice, Model> modelCollection;
    public static volatile CollectionAttribute<Lattice, BlsequenceLattice> blsequenceLatticeCollection;
    public static volatile SingularAttribute<Lattice, String> latticeName;
    public static volatile SingularAttribute<Lattice, Date> updateDate;
    public static volatile SingularAttribute<Lattice, Integer> latticeId;
    public static volatile SingularAttribute<Lattice, String> updatedBy;
    public static volatile SingularAttribute<Lattice, String> createdBy;
    public static volatile CollectionAttribute<Lattice, GoldLattice> goldLatticeCollection;
    public static volatile CollectionAttribute<Lattice, ElementProp> elementPropCollection;
    public static volatile SingularAttribute<Lattice, Date> createDate;
    public static volatile SingularAttribute<Lattice, MachineMode> machineModeId;

}