<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="3.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:rss="http://purl.org/rss/1.0/" 
  exclude-result-prefixes="rss">

  <!-- Template principal, match sur la racine du flux -->
  <xsl:template match="/rss:feed">
    <html>
      <head>
        <title>
          <xsl:text>Résumé de l'article</xsl:text>
        </title>
        <meta charset="UTF-8"/>
      </head>
      <body>
        <!-- Supposons que chaque article est un élément "entry" sous feed -->
        <xsl:for-each select="rss:entry">
          <article>
            <h1>
              <xsl:value-of select="title"/>
            </h1>

            <p><strong>GUID :</strong> <xsl:value-of select="guid"/></p>

            <xsl:if test="string-length(published) &gt; 0">
              <p><strong>Publié le :</strong> <xsl:value-of select="published"/></p>
            </xsl:if>

            <xsl:if test="string-length(updated) &gt; 0">
              <p><strong>Mise à jour le :</strong> <xsl:value-of select="updated"/></p>
            </xsl:if>

            <xsl:if test="string-length(category) &gt; 0">
              <p><strong>Catégorie :</strong> <xsl:value-of select="category"/></p>
            </xsl:if>

            <xsl:if test="string-length(authorName) &gt; 0">
              <p><strong>Auteur :</strong> <xsl:value-of select="authorName"/></p>
            </xsl:if>

            <xsl:if test="string-length(authorMail) &gt; 0">
              <p><strong>Contact :</strong> <xsl:value-of select="authorMail"/></p>
            </xsl:if>

            <xsl:if test="string-length(authorUri) &gt; 0">
              <p><strong>Site auteur :</strong> 
                <a href="{authorUri}">
                  <xsl:value-of select="authorUri"/>
                </a>
              </p>
            </xsl:if>

            <xsl:if test="string-length(contentSrc) &gt; 0">
              <p><strong>Contenu :</strong> <xsl:value-of select="contentSrc"/></p>
            </xsl:if>

            <xsl:if test="string-length(imageHref) &gt; 0">
              <div>
                <img src="{imageHref}">
                  <xsl:if test="string-length(imageAlt) &gt; 0">
                    <xsl:attribute name="alt">
                      <xsl:value-of select="imageAlt"/>
                    </xsl:attribute>
                  </xsl:if>
                </img>
              </div>
            </xsl:if>

            <xsl:if test="string-length(imageType) &gt; 0">
              <p><strong>Type image :</strong> <xsl:value-of select="imageType"/></p>
            </xsl:if>

            <xsl:if test="string-length(imageLength) &gt; 0">
              <p><strong>Taille image :</strong> <xsl:value-of select="imageLength"/></p>
            </xsl:if>

          </article>
          <hr/>
        </xsl:for-each>
      </body>
    </html>
  </xsl:template>

</xsl:stylesheet>
