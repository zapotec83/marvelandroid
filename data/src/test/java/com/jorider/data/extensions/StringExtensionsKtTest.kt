package com.jorider.data.extensions

import org.junit.Test

class StringExtensionsKtTest {

    @Test
    fun md5_ok() {
        assert("12345".md5().equals("827ccb0eea8a706c4c34a16891f84e7b"))
        assert("".md5().equals("d41d8cd98f00b204e9800998ecf8427e"))
        assert("AZERTYUIOP".md5().equals("11befe1b03f596c805ed03864def873d"))
        assert("azertyuiop".md5().equals("7682fe272099ea26efe39c890b33675b"))
    }
}