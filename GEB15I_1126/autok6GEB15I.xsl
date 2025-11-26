<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:key name="kVaros" match="auto" use="tualj/varos"/>

    <xsl:template match="/">
        <html>
            <body>
                <h2>Városok statisztikája</h2>
                <table border="1">
                    <tr><th>Város</th><th>Darabszám</th><th>Összár</th></tr>
                    <xsl:for-each select="autok/auto[generate-id() = generate-id(key('kVaros', tualj/varos)[1])]">
                        <tr>
                            <td><xsl:value-of select="tualj/varos"/></td>
                            <td><xsl:value-of select="count(key('kVaros', tualj/varos))"/></td>
                            <td><xsl:value-of select="sum(key('kVaros', tualj/varos)/ar)"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>