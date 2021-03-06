package dk.apaq.billy.repository;

import dk.apaq.billy.ErrorHandler;
import dk.apaq.billy.model.Entity;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.test.web.client.ResponseActions;
import org.springframework.web.client.RestTemplate;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import org.springframework.test.web.client.response.DefaultResponseCreator;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public abstract class ResourceTestBase<T extends Entity> {

    protected MockRestServiceServer mockServer;
    protected final Resource<T> resource;
    protected final RestTemplate restTemplate;
    protected final String resourceName;
    private Class type;

    public ResourceTestBase(Resource<T> resource, Class type) {
        this.resource = resource;
        this.resource.restTemplate.setErrorHandler(new ErrorHandler());
        this.restTemplate = resource.restTemplate;
        this.resourceName = resource.resourceName;
        this.type = type;
    }

    @Before
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }
    
    protected abstract String generateSingularId();
    protected T generatePersistenceEntity() throws ReflectiveOperationException {
        return (T) type.newInstance();
    }

    protected abstract String generateExpectedGetQueryParams();
    protected abstract DefaultResponseCreator generateExpectedGetResponse();
    protected abstract DefaultResponseCreator generateExpectedFindAllResponse();
    
    protected abstract List<RequestMatcher> generateExpectedSaveRequest();
    
    protected abstract void doCheckEntity(T entity);

    @Test
    public void testFindAll() {
        System.out.println("findAll");
        String queryParams = generateExpectedGetQueryParams();
        mockServer.expect(requestTo(resource.serviceUrl + "/" + resourceName + queryParams)).andExpect(method(HttpMethod.GET))
                .andRespond(generateExpectedFindAllResponse());
        List<T> entities = resource.findAll();
        for(T entity : entities) {
            doCheckEntity(entity);
        }
        
        mockServer.verify();
    }

    @Test
    public void testGet() {
        System.out.println("get");
        String id = generateSingularId();
        
        String queryParams = generateExpectedGetQueryParams();
        mockServer.expect(requestTo(resource.serviceUrl + "/" + resourceName + "/" + id + queryParams)).andExpect(method(HttpMethod.GET))
                .andRespond(generateExpectedGetResponse());
        T entity = resource.get(id);
        doCheckEntity(entity);
        
        mockServer.verify();
    }
    
    @Test
    public void testGetNotFound() {
        System.out.println("get");
        String id = "NONE";
        
        String queryParams = generateExpectedGetQueryParams();
        mockServer.expect(requestTo(resource.serviceUrl + "/" + resourceName + "/" + id + queryParams)).andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND).body(Util.readResourceFromFile("error_not_found.json")).contentType(MediaType.APPLICATION_JSON));
        T entity = resource.get(id);
        assertNull(entity);
        mockServer.verify();
    }

    @Test
    public void testSave() throws ReflectiveOperationException {
        System.out.println("save");
        String id = generateSingularId();
        
        T entity = generatePersistenceEntity();
        entity.setId(id);
        ResponseActions actions = mockServer.expect(requestTo(resource.serviceUrl + "/" + resourceName + "/" + id)).andExpect(method(HttpMethod.PUT));
        
        for(RequestMatcher m : generateExpectedSaveRequest()) {
            actions = actions.andExpect(m);
        }
        
        actions.andRespond(generateExpectedGetResponse());
        
        
        resource.save(entity);
        mockServer.verify();
        
    }

    
    public static RestTemplate buildRestTemplate() {
        RestTemplate rt = new RestTemplate();
        //RestTemplateHelper.configureForBillyJargon(rt);
        return rt;
    }
}
