package org.simple.security;

import java.io.Serializable;

import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;

public class NullAclCache implements AclCache {
	
	@Override
	public void clearCache() {
	}

	@Override
	public void evictFromCache(Serializable arg0) {
	}

	@Override
	public void evictFromCache(ObjectIdentity arg0) {
	}

	@Override
	public MutableAcl getFromCache(ObjectIdentity arg0) {
		return null;
	}

	@Override
	public MutableAcl getFromCache(Serializable arg0) {
		return null;
	}

	@Override
	public void putInCache(MutableAcl arg0) {
	}
}