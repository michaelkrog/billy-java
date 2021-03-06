package dk.apaq.billy.repository;

import dk.apaq.billy.mapping.PersistOrganizationMapping;
import dk.apaq.billy.mapping.PluralOrganizationMapping;
import dk.apaq.billy.mapping.SingularMapping;
import dk.apaq.billy.mapping.SingularOrganizationMapping;
import dk.apaq.billy.model.Organization;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class OrganizationResource extends Resource<Organization>{
    
    private static final Map<String, String> SIDELOAD_PARAMS = new HashMap<>();
    
    static {
    //    SIDELOAD_PARAMS.put("include", "organization.lines:embed");
    }
    
    public OrganizationResource(RestTemplate restTemplate, String serviceUrl, String organizationId) {
        super(SingularOrganizationMapping.class, PluralOrganizationMapping.class, PersistOrganizationMapping.class,
                "organizations", restTemplate, serviceUrl, SIDELOAD_PARAMS, organizationId);
    }
    
    public Organization getCurrent() {
        URI url;
    
        try {
            if(organizationId == null) {
                url = new URI(serviceUrl + "/organization");
            } else {
                url = new URI(serviceUrl + "/organizations/" + organizationId);
            }
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("Url is not valid.", ex);
        }
        
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(url);
        
        for (Map.Entry<String, String> entry : sideloadParams.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }

        url = builder.build().encode().toUri();
        SingularMapping<Organization> result = (SingularMapping<Organization>) restTemplate.getForObject(url, singularClass);
        return result == null ? null : result.getEntity();
    }
}