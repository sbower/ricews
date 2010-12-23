/*
 * Copyright 2007-2010 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package advws.net.ricews;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.xml.ws.WebServiceContext;

import org.apache.cxf.transport.http.AbstractHTTPDestination;

/**
 * Implementation for {@link BaseballCardCollectionService}
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 *
 */
public class BaseballCardCollectionServiceImplA implements BaseballCardCollectionServiceA {
    
    private Map<Integer, BaseballCardA> cards = new ConcurrentHashMap<Integer, BaseballCardA>();
    private AtomicInteger nextId = new AtomicInteger(1);
        
    /**
     * @see org.kuali.rice.ksb.messaging.BaseballCardCollectionServiceA.BaseballCardCollectionService#add(org.kuali.rice.ksb.messaging.remotedservices.BaseballCard)
     */
    
   public Integer add(HttpHeaders hh, BaseballCardA card) {
   
   System.out.println(hh.getRequestHeader(hh.USER_AGENT));
   Integer result = null;
        if (card != null) {
            int id = nextId.addAndGet(1);
            cards.put(id, card);
            result = id;
        }
        return result;
    }

    /**
     * @see org.kuali.rice.ksb.messaging.BaseballCardCollectionServiceA.BaseballCardCollectionService#delete(java.lang.Integer)
     */
    public void delete(Integer id) {
        cards.remove(id);
    }

    /**
     * @see org.kuali.rice.ksb.messaging.BaseballCardCollectionServiceA.BaseballCardCollectionService#get(java.lang.Integer)
     */
    public BaseballCardA get(Integer id) {
        return cards.get(id);
    }

    /**
     * @see org.kuali.rice.ksb.messaging.BaseballCardCollectionServiceA.BaseballCardCollectionService#get(java.lang.String)
     */
    public List<BaseballCardA> get(String playerName) {
        List<BaseballCardA> results = new ArrayList<BaseballCardA>();
        for (BaseballCardA card : cards.values()) {
            if (playerName.equals(card.getPlayerName())) results.add(card);
        }
        return results;
    }

    /**
     * @see org.kuali.rice.ksb.messaging.BaseballCardCollectionServiceA.BaseballCardCollectionService#getAll()
     */
    public List<BaseballCardA> getAll() {
        return new ArrayList<BaseballCardA>(cards.values());
    }

    /**
     * @see org.kuali.rice.ksb.messaging.BaseballCardCollectionServiceA.BaseballCardCollectionService#update(java.lang.Integer, org.kuali.rice.ksb.messaging.remotedservices.BaseballCard)
     */
    public void update(Integer id, BaseballCardA card) {
        cards.put(id, card);
    }
    
    /**
     * This method lacks JAX-RS annotations in the {@link BaseballCardCollectionService} interface
     * 
     * @see org.kuali.rice.ksb.messaging.BaseballCardCollectionServiceA.BaseballCardCollectionService#unannotatedMethod()
     */
    public void unannotatedMethod() {
        // do nothing
    }

}
