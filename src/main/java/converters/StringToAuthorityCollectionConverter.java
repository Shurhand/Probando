package converters;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import security.Authority;

@Component
@Transactional
public class StringToAuthorityCollectionConverter implements Converter<String,Collection<Authority>> {

	@Override
	public Collection<Authority> convert(String text){
		Collection<Authority> auths = new ArrayList<>();
		Authority auth;
		System.out.println(text);
		if(text.startsWith("CONSUMER")){	
			try {
				if(StringUtils.isEmpty(text)){
					auth = null;
				}else{
					auth = new Authority();
					auth.setAuthority(text);
					auths.add(auth);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}		
		
	}
		return auths;
	}
	
}

