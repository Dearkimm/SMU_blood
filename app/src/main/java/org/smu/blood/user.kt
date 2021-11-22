
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
class User {
    var userName: String? = null
    var email: String? = null

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    constructor(userName: String?, email: String?) {
        this.userName = userName
        this.email = email
    }

    override fun toString(): String {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}'
    }
}