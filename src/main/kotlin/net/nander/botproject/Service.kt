package net.nander.botproject

import de.thjom.java.systemd.Systemd
import de.thjom.java.systemd.Unit

class Service(name: String) {
    private val service = Systemd.get(Systemd.InstanceType.SYSTEM).manager.getService(name)!!
    fun start(): Boolean {
        service.start(Unit.Mode.FAIL)
        return false
    }

    fun stop(): Boolean {
        service.stop(Unit.Mode.FAIL)
        return false
    }

    fun status(): String {
        return service.statusText ?: "Error"
    }

}