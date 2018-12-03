package hung.org.oauth2.web;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserInfoController {

	//@Autowired
	//private UserAccessFunctionMapper userAccessFuncMapper;
	
    @RequestMapping("/oauth/userinfo")    
    public Object user(
		@RequestParam(required=false,defaultValue="EM") String applnCode,
		Principal principal
    ) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	
    	log.info("Authentication.class:"+auth);
    	log.info("Principal.class:"+auth.getPrincipal());
    	
    	if (auth instanceof OAuth2Authentication) {
    		OAuth2Authentication oauth2Auth = (OAuth2Authentication)auth;
    	}
    	
    	//if (auth.getPrincipal() instanceof ADUserDetailsImpl) {
    	//	ADUserDetailsImpl userdetails = (ADUserDetailsImpl)auth.getPrincipal();
    	//	List<UserAccessFunction> userAccessFuncs = userAccessFuncMapper.findUserAccessFunctionByApplnAndUser("'"+applnCode+"'",userdetails.getGLUserCode());
    	//	userdetails.setUserAccessFunc(userAccessFuncs);
    	//}
    	
    	return auth.getPrincipal();
    }
    
}
