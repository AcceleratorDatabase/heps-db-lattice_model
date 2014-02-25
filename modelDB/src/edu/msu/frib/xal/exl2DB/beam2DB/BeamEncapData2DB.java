/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.msu.frib.xal.exl2DB.beam2DB;

import edu.msu.frib.xal.exl2DB.lat_mod2DB.ReadComSheet;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import org.apache.poi.ss.usermodel.Workbook;
import org.openepics.model.api.BeamParameterAPI;
import org.openepics.model.api.BeamParameterPropAPI;
import org.openepics.model.api.BeamlineSequenceAPI;
import org.openepics.model.api.ElementAPI;
import org.openepics.model.api.ModelAPI;
import org.openepics.model.api.ParticleTypeAPI;
import org.openepics.model.entity.BeamParameter;
import org.openepics.model.entity.BeamlineSequence;
import org.openepics.model.entity.Element;
import org.openepics.model.entity.Model;
import org.openepics.model.entity.ParticleType;

/**
 *
 * @author lv
 * @author chu
 */
public class BeamEncapData2DB {

    public static void instDB(Workbook wb, String sheetName, String latticeName, String created_by, Date create_date) {
        ArrayList particleList = ReadBeamSheet.getParticleList(wb, sheetName);
        ArrayList encapDataList = BeamDataEncap.getEncapData(wb, sheetName);
        ArrayList modelNameList = ReadComSheet.getColList(wb, sheetName, "model/name", "DB label");
        if (particleList.size() == encapDataList.size() && particleList.size() == modelNameList.size()) {
            for (int i = 0; i < particleList.size(); i++) {
                String model_name = modelNameList.get(i).toString();
                new ModelAPI().setModelForInit(model_name, latticeName, created_by, create_date);
                BeamlineSequence beamline_sequence = new BeamlineSequenceAPI().getSequenceByName(model_name);
                Element e = null;
                if (beamline_sequence == null) {
                    System.out.println("The Beamline Sequence " + model_name + " doesn't exist!");
                } else {
                    String first_ele_name = beamline_sequence.getFirstElementName();
                    e = new ElementAPI().getElementByName(first_ele_name);
                    if (e == null) {
                        System.out.println("The element " + first_ele_name + " doesn't exist");
                    }
                }
                Map particleMap = (Map) particleList.get(i);
                String particle_name = (String) particleMap.get("particle_name");
                Double particle_mass = (Double) particleMap.get("particle_mass");
                int particle_charge = (int) Double.parseDouble(particleMap.get("particle_charge").toString());

                ParticleType particleType = new ParticleTypeAPI().getParticleType(particle_name, particle_mass, particle_charge);
                if (particleType == null) {
                    particleType = new ParticleTypeAPI().setParticleType(particle_name, particle_mass, particle_charge);
                    // particleType = new ParticleTypeAPI().getParticleType(particle_name, particle_mass, particle_charge);
                }
                ArrayList rowClsList = (ArrayList) encapDataList.get(i);
                Model model = new ModelAPI().getModelForName(model_name);
                BeamParameter beamParameter = new BeamParameterAPI().setBeamParameter(e, model, particleType,0);               
                Iterator it = rowClsList.iterator();
                while (it.hasNext()) {
                    BeamCell cellProp = (BeamCell) it.next();
                    if ("beam_parameter_prop".equals(cellProp.getTableName())) {
                        new BeamParameterPropAPI().setBeamParameterProp(beamParameter, cellProp.getCategory(), cellProp.getName(), cellProp.getDatatype(), cellProp.getValue(), null);
                    }
                }
            }

        } else {
            System.out.println("There is something wrong!");
        }
    }
}
