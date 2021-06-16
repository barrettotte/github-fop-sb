<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">

    <!-- main -->
    <xsl:template match="github-user">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4" page-height="11in" page-width="8.5in" 
                  margin-top="0.5in" margin-bottom="0.5in" margin-left="1in" margin-right="1in">
                    <fo:region-body/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <fo:page-sequence master-reference="A4">
                <fo:flow flow-name="xsl-region-body">
                    <xsl:call-template name="header"/>
                    <xsl:call-template name="repo-table"/>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <!-- Generate fo:block GitHub user header -->
    <xsl:template name="header">
        <fo:block font-size="20pt" font-weight="bold" space-after="2mm" text-align="center">
            <xsl:value-of select="profile/name"/>
            <xsl:text>'s Repositories</xsl:text>
        </fo:block>
        <fo:block font-size="13pt" text-align="center">
            <xsl:call-template name="githubLink">
                <xsl:with-param name="username">
                    <xsl:value-of select="profile/username"/>
                </xsl:with-param>
            </xsl:call-template>
        </fo:block>
        <fo:block border-bottom-width="2pt" border-bottom-style="solid" margin-top="7mm" space-after="8mm"/>
    </xsl:template>

    <!-- Generate fo:table for all user's repositories -->
    <xsl:template name="repo-table">
        <xsl:variable name="username">
            <xsl:value-of select="profile/username"/>
        </xsl:variable>

        <fo:table width="100%" border-collapse="separate" border-spacing="5pt 15pt">
            <fo:table-column column-width="45mm"/>
            <fo:table-column column-width="50mm"/>
            <fo:table-column column-width="75mm"/>
            <fo:table-body>
                <xsl:for-each select="repositories/repo">
                    <xsl:call-template name="repo-row">
                        <xsl:with-param name="username">
                            <xsl:value-of select="$username"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
            </fo:table-body>
        </fo:table>
    </xsl:template>

    <!-- Generate fo:table-row for a single repository -->
    <xsl:template name="repo-row">
        <xsl:param name="username"/>

        <fo:table-row>
            <fo:table-cell>
                <fo:block font-size="14pt">
                    <xsl:call-template name="githubLink">
                        <xsl:with-param name="username">
                            <xsl:value-of select="$username"/>
                        </xsl:with-param>
                        <xsl:with-param name="repoName">
                            <xsl:value-of select="@name"/> 
                        </xsl:with-param>
                        <xsl:with-param name="linkText">
                            <xsl:value-of select="@name"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block font-size="12pt">
                    <xsl:value-of select="@stars"/>
                    <xsl:text>&#160;star(s)</xsl:text>
                    <xsl:text>&#160;&#160;</xsl:text>
                    <xsl:value-of select="@forks"/>
                    <xsl:text>&#160;fork(s)</xsl:text>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block font-size="12pt">
                    <fo:block>
                        <xsl:for-each select="languages/language">
                            <xsl:value-of select="."/>
                            <xsl:text>&#160;&#160;</xsl:text>
                        </xsl:for-each>
                    </fo:block>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>

    <xsl:template name="githubLink">
        <xsl:param name="username"/>
        <xsl:param name="repoName"/>
        <xsl:param name="linkText"/>

        <xsl:variable name="location">
            <xsl:value-of select="$username"/>
            <xsl:if test="$repoName">    
                <xsl:text>/</xsl:text>
                <xsl:value-of select="$repoName"/>
            </xsl:if>
        </xsl:variable>
        <xsl:variable name="link">
            <xsl:text>https://github.com/</xsl:text>
            <xsl:value-of select="$location"/>
        </xsl:variable>

        <fo:basic-link external-destination="{$link}" color="blue" text-decoration="underline">
            <xsl:choose>
                <xsl:when test="$linkText">
                    <xsl:value-of select="$linkText"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$link"/>
                </xsl:otherwise>
            </xsl:choose>
        </fo:basic-link>
    </xsl:template>

</xsl:stylesheet>