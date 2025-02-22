package one.breece.track_rejoice.web.dto

import com.fasterxml.jackson.annotation.*
import java.util.*


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("success", "score", "action", "challenge_ts", "hostname", "error-codes")
class GoogleResponse {
    @JsonProperty("success")
     var success = false

    @JsonProperty("challenge_ts")
     var challengeTimeStamp: String? = null

    @JsonProperty("hostname")
     var hostname: String? = null

    @JsonProperty("score")
     var score = 0f

    @JsonProperty("action")
     var action: String? = null

    @JsonProperty("error-codes")
    private var errorCodes: Array<ErrorCode> = emptyArray()


    enum class ErrorCode {
        MissingSecret, InvalidSecret, MissingResponse, InvalidResponse, BadRequest, TimeoutOrDuplicate;

        companion object {
            private val errorsMap: MutableMap<String, ErrorCode> = HashMap(6)

            init {
                errorsMap["missing-input-secret"] = MissingSecret
                errorsMap["invalid-input-secret"] = InvalidSecret
                errorsMap["missing-input-response"] = MissingResponse
                errorsMap["invalid-input-response"] = InvalidResponse
                errorsMap["bad-request"] = BadRequest
                errorsMap["timeout-or-duplicate"] = TimeoutOrDuplicate
            }

            @JsonCreator
            fun forValue(value: String): ErrorCode? {
                return errorsMap[value.lowercase(Locale.getDefault())]
            }
        }
    }

    @JsonIgnore
    fun hasClientError(): Boolean {
        val errors: Array<ErrorCode> = errorCodes
        for (error in errors) {
            when (error) {
                ErrorCode.InvalidResponse, ErrorCode.MissingResponse, ErrorCode.BadRequest -> return true
                else -> {}
            }
        }
        return false
    }

    override fun toString(): String {
        return "GoogleResponse{" + "success=" + success + ", challengeTs='" + challengeTimeStamp + '\'' + ", hostname='" + hostname + '\'' + ", score='" + score + '\'' + ", action='" + action + '\'' + ", errorCodes=" + errorCodes.contentToString() + '}'
    }
}