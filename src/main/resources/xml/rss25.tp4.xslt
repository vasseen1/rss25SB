<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:rss="http://univ.fr/rss25"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                version="3.0">

  <xsl:output method="html" version="5.0" encoding="UTF-8" indent="yes"/>

  <xsl:template match="/rss:feed">
    <html>
      <head>
        <title>TP4 - Flux RSS25</title>
        <link rel="stylesheet" type="text/css" href="rss25.tp4.css" />
      </head>
      <body>
        <h1>TP4 - Flux RSS25</h1>

        <p>Le 
          <xsl:value-of select="format-date(current-date(), '[D]')"/> 
          <xsl:choose>
            <xsl:when test="format-date(current-date(), '[M]') = '1'"> Jan </xsl:when>
            <xsl:when test="format-date(current-date(), '[M]') = '2'"> Fév </xsl:when>
            <xsl:when test="format-date(current-date(), '[M]') = '3'"> Mar </xsl:when>
            <xsl:when test="format-date(current-date(), '[M]') = '4'"> Avr </xsl:when>
            <xsl:when test="format-date(current-date(), '[M]') = '5'"> Mai </xsl:when>
            <xsl:when test="format-date(current-date(), '[M]') = '6'"> Juin </xsl:when>
            <xsl:when test="format-date(current-date(), '[M]') = '7'"> Juil </xsl:when>
            <xsl:when test="format-date(current-date(), '[M]') = '8'"> Aout </xsl:when>
            <xsl:when test="format-date(current-date(), '[M]') = '9'"> Sept </xsl:when>
            <xsl:when test="format-date(current-date(), '[M]') = '10'"> Oct </xsl:when>
            <xsl:when test="format-date(current-date(), '[M]') = '11'"> Nov </xsl:when>
            <xsl:when test="format-date(current-date(), '[M]') = '12'"> Déc </xsl:when>
          </xsl:choose>
          <xsl:value-of select="format-date(current-date(), '[Y]')"/>
        </p>

        <h2>Description</h2>
        <ul>
          <li> Contenu : Physique : <xsl:value-of select="rss:title"/></li>
          <li>publié le 
            <xsl:variable name="pubDate" select="substring-before(rss:pubDate, 'T')"/>
            <xsl:if test="$pubDate">
              <xsl:value-of select="format-date(xs:date($pubDate), '[D] [MNn] [Y]')"/>
            </xsl:if>
          </li>
          <li>copyright <xsl:value-of select="rss:copyright"/></li>
        </ul>

        <h2>Synthèse des articles</h2>
        <table border="1">
          <thead>
            <tr>
              <th>Numéro</th>
              <th>Titre</th>
              <th>Date</th>
              <th>Catégories</th>
              <th>Auteurs</th>
            </tr>
          </thead>
          <tbody>
            <!-- Trier les items par date de publication ou mise à jour (plus récent en premier) -->
            <xsl:for-each select="rss:item">
              <xsl:sort select="if (rss:published) then xs:date(substring-before(rss:published, 'T')) else if (rss:updated) then xs:date(substring-before(rss:updated, 'T')) else xs:date('1900-01-01')" order="descending" data-type="text"/>
              <tr>
                <td>
                  <xsl:value-of select="position()"/> / <xsl:value-of select="count(/rss:feed/rss:item)"/>
                </td>

                <td>
                  <xsl:choose>
                    <xsl:when test="string-length(rss:title) &gt; 45">
                      <xsl:value-of select="substring(rss:title, 1, 45)"/>...
                    </xsl:when>
                    <xsl:otherwise>
                      <xsl:value-of select="rss:title"/>
                    </xsl:otherwise>
                  </xsl:choose>
                </td>

                <td>
                  <xsl:choose>
                    <xsl:when test="rss:published">
                      <xsl:value-of select="substring-before(rss:published, 'T')"/>
                    </xsl:when>
                    <xsl:when test="rss:updated">
                      <xsl:value-of select="substring-before(rss:updated, 'T')"/>
                    </xsl:when>
                    <xsl:otherwise>Non spécifié</xsl:otherwise>
                  </xsl:choose>
                </td>

                <td>
                  <xsl:value-of select="rss:category/@term"/>
                </td>

                <xsl:choose>
                  <xsl:when test="rss:author">
                    <td class="Auteur"><xsl:value-of select="rss:author/rss:name"/></td>
                  </xsl:when>
                  <xsl:when test="rss:contributor">
                    <td class="Contributeur"><xsl:value-of select="rss:contributor/rss:name"/></td>
                  </xsl:when>
                  <xsl:otherwise>
                    <td>Non spécifié</td>
                  </xsl:otherwise>
                </xsl:choose>
              </tr>
            </xsl:for-each>
          </tbody>
        </table>

        <h2>Détails des informations</h2>
        <xsl:for-each select="rss:item">
          <h3><xsl:value-of select="rss:title"/></h3>
          <em>(guid = <xsl:value-of select="rss:guid"/>)</em>

          <xsl:if test="rss:image">
            <div>
              <img src="{rss:image/@href}" alt="{rss:image/@alt}" width="500" height="300"/>
            </div>
          </xsl:if>

          <p>Catégorie : <xsl:value-of select="rss:category/@term"/></p>

          <xsl:if test="rss:published">
            <p>Publié le 
              <xsl:value-of select="format-date(xs:date(substring-before(rss:published, 'T')), '[D]')"/> 
              <xsl:choose>
                <xsl:when test="format-date(xs:date(substring-before(rss:published, 'T')), '[M]') = '1'"> Jan </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:published, 'T')), '[M]') = '2'"> Fév </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:published, 'T')), '[M]') = '3'"> Mar </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:published, 'T')), '[M]') = '4'"> Avr </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:published, 'T')), '[M]') = '5'"> Mai </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:published, 'T')), '[M]') = '6'"> Juin </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:published, 'T')), '[M]') = '7'"> Juil </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:published, 'T')), '[M]') = '8'"> Aout </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:published, 'T')), '[M]') = '9'"> Sept </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:published, 'T')), '[M]') = '10'"> Oct </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:published, 'T')), '[M]') = '11'"> Nov </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:published, 'T')), '[M]') = '12'"> Déc </xsl:when>
              </xsl:choose>
              <xsl:value-of select="format-date(xs:date(substring-before(rss:published, 'T')), '[Y]')"/>
            </p>
          </xsl:if>

          <xsl:if test="rss:updated">
            <p>Mise à jour le 
              <xsl:value-of select="format-date(xs:date(substring-before(rss:updated, 'T')), '[D]')"/> 
              <xsl:choose>
                <xsl:when test="format-date(xs:date(substring-before(rss:updated, 'T')), '[M]') = '1'"> Jan </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:updated, 'T')), '[M]') = '2'"> Fév </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:updated, 'T')), '[M]') = '3'"> Mar </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:updated, 'T')), '[M]') = '4'"> Avr </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:updated, 'T')), '[M]') = '5'"> Mai </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:updated, 'T')), '[M]') = '6'"> Juin </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:updated, 'T')), '[M]') = '7'"> Juil </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:updated, 'T')), '[M]') = '8'"> Aout </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:updated, 'T')), '[M]') = '9'"> Sept </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:updated, 'T')), '[M]') = '10'"> Oct </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:updated, 'T')), '[M]') = '11'"> Nov </xsl:when>
                <xsl:when test="format-date(xs:date(substring-before(rss:updated, 'T')), '[M]') = '12'"> Déc </xsl:when>
              </xsl:choose>
              <xsl:value-of select="format-date(xs:date(substring-before(rss:updated, 'T')), '[Y]')"/>
            </p>
          </xsl:if>

          <p><xsl:value-of select="rss:content"/></p>

          <xsl:if test="rss:author">
            <p>Auteur : <xsl:value-of select="rss:author/rss:name"/></p>
          </xsl:if>

          <xsl:if test="rss:contributor">
            <p>Contributeur : <xsl:value-of select="rss:contributor/rss:name"/></p>
          </xsl:if>
        </xsl:for-each>

      </body>
    </html>
  </xsl:template>

</xsl:stylesheet>
