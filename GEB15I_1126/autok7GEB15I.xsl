<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="/">
        <eredmeny>
            <xsl:for-each select="autok/auto">
                <xsl:sort select="ar" data-type="number" order="ascending"/>
                <auto_info>
                    <rendszam><xsl:value-of select="@rsz"/></rendszam>
                    <ar><xsl:value-of select="ar"/></ar>
                </auto_info>
            </xsl:for-each>
        </eredmeny>
    </xsl:template>
</xsl:stylesheet>