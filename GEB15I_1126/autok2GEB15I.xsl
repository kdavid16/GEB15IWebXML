<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Drága autók száma (>30000)</h2>
                <p>Darabszám: 
                    <strong>
                        <xsl:value-of select="count(autok/auto[ar > 30000])"/>
                    </strong>
                </p>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>