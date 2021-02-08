package informative.rest

import informative.SpecType

class Visited {
    Long timeStamp = System.currentTimeMillis()
    static constraints = {
        timeStamp nullable: true
    }
}
