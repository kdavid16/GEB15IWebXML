<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  <head>
    <style>
      table {
        border-collapse: collapse;
        border: 2px solid black;
      }
      th {
        background-color: #9acd32; /* A képen látható zöldes szín */
        color: black;
        text-align: left;
        padding: 5px;
        border: 1px solid gray;
      }
      td {
        padding: 5px;
        border: 1px solid gray;
      }
    </style>
  </head>
  <body>
    <h2>Mérnökinformatika BSc - Órarend</h2>
    <table>
      <thead>
        <tr>
          <th>Nap</th>
          <th>Időpont</th>
          <th>Tárgy</th>
          <th>Típus</th>
          <th>Helyszín</th>
          <th>Oktató</th>
        </tr>
      </thead>
      <tbody>
        <xsl:for-each select="KD_orarend/ora">
          <tr>
            <xsl:attribute name="class">
              <xsl:value-of select="@tipus"/>
            </xsl:attribute>
            
            <td><xsl:value-of select="idopont/nap"/></td>
            <td>
              <xsl:value-of select="idopont/tol"/> - <xsl:value-of select="idopont/ig"/>
            </td>
            <td><strong><xsl:value-of select="targy"/></strong></td>
            <td>
              <span>
                <xsl:attribute name="class">
                  <xsl:text>tipus-badge badge-</xsl:text><xsl:value-of select="@tipus"/>
                </xsl:attribute>
                <xsl:choose>
                    <xsl:when test="@tipus='elmelet'">Elmélet</xsl:when>
                    <xsl:when test="@tipus='gyakorlat'">Gyakorlat</xsl:when>
                    <xsl:otherwise><xsl:value-of select="@tipus"/></xsl:otherwise>
                </xsl:choose>
                
              </span>
            </td>
            <td><xsl:value-of select="helyszin"/></td>
            <td><xsl:value-of select="oktato"/></td>
          </tr>
        </xsl:for-each>
      </tbody>
    </table>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>