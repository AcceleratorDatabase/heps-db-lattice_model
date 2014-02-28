package org.openepics.model.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.openepics.model.entity.Lattice;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-02-27T18:50:52")
@StaticMetamodel(GoldLattice.class)
public class GoldLattice_ { 

    public static volatile SingularAttribute<GoldLattice, String> createdBy;
    public static volatile SingularAttribute<GoldLattice, Integer> goldId;
    public static volatile SingularAttribute<GoldLattice, Date> createDate;
    public static volatile SingularAttribute<GoldLattice, Integer> goldStatusInd;
    public static volatile SingularAttribute<GoldLattice, Date> updateDate;
    public static volatile SingularAttribute<GoldLattice, String> machineModeId;
    public static volatile SingularAttribute<GoldLattice, String> updatedBy;
    public static volatile SingularAttribute<GoldLattice, Lattice> latticeId;

}