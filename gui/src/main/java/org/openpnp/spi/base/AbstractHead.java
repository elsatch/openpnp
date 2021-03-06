package org.openpnp.spi.base;

import java.util.Collections;
import java.util.List;

import org.openpnp.spi.Actuator;
import org.openpnp.spi.Camera;
import org.openpnp.spi.Head;
import org.openpnp.spi.Machine;
import org.openpnp.spi.Nozzle;
import org.openpnp.util.IdentifiableList;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.core.Commit;

public abstract class AbstractHead implements Head {
    protected Machine machine;
    @Attribute
    protected String id;
    @ElementList(required=false)
    protected IdentifiableList<Nozzle> nozzles = new IdentifiableList<Nozzle>();
    @ElementList(required=false)
    protected IdentifiableList<Actuator> actuators = new IdentifiableList<Actuator>();
    @ElementList(required=false)
    protected IdentifiableList<Camera> cameras = new IdentifiableList<Camera>();
    
    @SuppressWarnings("unused")
    @Commit
    private void commit() {
        for (Nozzle nozzle : nozzles) {
            nozzle.setHead(this);
        }
        for (Camera camera : cameras) {
            camera.setHead(this);
        }
        for (Actuator actuator : actuators) {
            actuator.setHead(this);
        }
    }
    
    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<Nozzle> getNozzles() {
        return Collections.unmodifiableList(nozzles);
    }

    @Override
    public Nozzle getNozzle(String id) {
        return nozzles.get(id);
    }

    @Override
    public List<Actuator> getActuators() {
        return Collections.unmodifiableList(actuators);
    }

    @Override
    public Actuator getActuator(String id) {
        return actuators.get(id);
    }

    @Override
    public List<Camera> getCameras() {
        return Collections.unmodifiableList(cameras);
    }

    @Override
    public Camera getCamera(String id) {
        return cameras.get(id);
    }

    @Override
    public void addCamera(Camera camera) throws Exception {
        cameras.add(camera);
    }

    @Override
    public void removeCamera(Camera camera) {
        cameras.remove(camera);
    }
    
    @Override
    public void moveToSafeZ(double speed) throws Exception {
        for (Nozzle nozzle : nozzles) {
            nozzle.moveToSafeZ(speed);
        }
        for (Camera camera : cameras) {
            camera.moveToSafeZ(speed);
        }
        for (Actuator actuator : actuators) {
            actuator.moveToSafeZ(speed);
        }
    }
}
