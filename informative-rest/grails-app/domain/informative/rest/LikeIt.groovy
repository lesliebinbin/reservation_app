package informative.rest

import informative.SpecType

class LikeIt {
    SpecType type
    static constraints = {
    }
    static mapping = {
        type enumType: 'string'
    }
}
