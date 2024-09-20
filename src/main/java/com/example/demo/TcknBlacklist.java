package com.example.demo;

import java.util.Set;

class TcknBlacklist {

    boolean isBlacklisted(String tckn) {
        // Assume this is a 3rd party rest call
        return Set.of("96158515640", "10897113684", "91390193410").contains(tckn);
    }
}
