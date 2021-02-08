package informative.rest

import informative.SpecType

class ShareIt {
    SpecType type
    static constraints = {
    }
    static mapping = {
        type enumType: 'string'
    }
}
