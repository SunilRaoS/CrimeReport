package com.happiestminds.civiccop.service.webservices;

import de.greenrobot.event.EventBus;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Created by ravindra.kambale on 8/28/2015.
 */
public class RestErrorHandler implements ErrorHandler {

    public static final int HTTP_NOT_FOUND = 404;
    public static final int INVALID_LOGIN_PARAMETERS = 101;

    private EventBus bus;

    public RestErrorHandler(EventBus bus) {
        this.bus = bus;
    }

    @Override
    public Throwable handleError(RetrofitError cause) {
        if(cause != null) {
            if (cause.isNetworkError()) {
               // bus.post(new NetworkErrorEvent(cause));
            } else if(isUnAuthorized(cause)) {
               // bus.post(new UnAuthorizedErrorEvent(cause));
            } else {
                //bus.post(new RestAdapterErrorEvent(cause));
            }
        }

        // Example of how you'd check for a unauthorized result
        // if (cause != null && cause.getStatus() == 401) {
        //     return new UnauthorizedException(cause);
        // }

        // You could also put some generic error handling in here so you can start
        // getting analytics on error rates/etc. Perhaps ship your logs off to
        // Splunk, Loggly, etc

        return cause;
    }

    private boolean isUnAuthorized(RetrofitError cause) {
        boolean authFailed = false;

        if(cause.getResponse().getStatus() == HTTP_NOT_FOUND) {
           /* final ApiError err = (ApiError) cause.getBodyAs(ApiError.class);
            if(err != null && err.getCode() == INVALID_LOGIN_PARAMETERS) {
                authFailed = true;
            }*/
        }

        return authFailed;
    }

}
