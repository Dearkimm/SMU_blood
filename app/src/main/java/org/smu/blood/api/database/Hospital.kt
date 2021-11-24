package org.smu.blood.api.database

class Hospital {
    var id: Int? = null
    var name: String? = null

    constructor()
    constructor(id: Int?, name: String?) {
        this.id = id
        this.name = name
    }

    override fun toString(): String {
        return "Hospital(id=$id, name=$name)"
    }

}