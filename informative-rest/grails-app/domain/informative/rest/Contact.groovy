package informative.rest

class Contact {
    String name
    String mobileNo
    String email

    static constraints = {
        email email: true
        mobileNo blank: false
        name blank: false
    }

}
