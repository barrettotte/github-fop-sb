<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
  xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
    
    <xsl:output method="xml" indent="yes"/>

    <!-- main -->
    <xsl:template match="user">
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
                    <xsl:call-template name="repoTable"/>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>

    <!-- Generate fo:block GitHub user header -->
    <xsl:template name="header">
        <fo:block font-size="20pt" font-weight="bold" space-after="2mm" text-align="center">
            <xsl:value-of select="@name"/>
        </fo:block>
        <fo:block font-size="13pt" text-align="center">
            <xsl:call-template name="githubLink">
                <xsl:with-param name="username">
                    <xsl:value-of select="@username"/>
                </xsl:with-param>
            </xsl:call-template>
        </fo:block>
        <fo:block border-bottom-width="2pt" border-bottom-style="solid" margin-top="7mm" space-after="8mm"/>
    </xsl:template>

    <!-- Generate fo:table for all user's repositories -->
    <xsl:template name="repoTable">
        <xsl:variable name="username">
            <xsl:value-of select="@username"/>
        </xsl:variable>

        <fo:table width="100%" border-collapse="separate" border-spacing="5pt 15pt">
            <fo:table-column column-width="70mm"/>
            <fo:table-column column-width="20mm"/>
            <fo:table-column column-width="90mm"/>
            <fo:table-body>
                <xsl:for-each select="repositories/repo">
                    <xsl:sort select="@name"/>
                    <xsl:call-template name="repoRow">
                        <xsl:with-param name="username">
                            <xsl:value-of select="$username"/>
                        </xsl:with-param>
                    </xsl:call-template>
                </xsl:for-each>
            </fo:table-body>
        </fo:table>
    </xsl:template>

    <!-- Generate fo:table-row for a single repository -->
    <xsl:template name="repoRow">
        <xsl:param name="username"/>

        <fo:table-row>
            <fo:table-cell>
                <fo:block font-size="12pt">
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
                    <xsl:text>&#160;&#9733;&#160;</xsl:text>    
                    <xsl:value-of select="@stars"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell>
                <fo:block font-size="12pt">
                    <fo:block>
                        <xsl:for-each select="languages/language">
                            <xsl:call-template name="languageColor">
                                <xsl:with-param name="colorHex">
                                    <xsl:choose>
                                        <xsl:when test="@color = 'null'">
                                            <xsl:text>#CCCCCC</xsl:text>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:value-of select="@color"/>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:with-param>
                            </xsl:call-template>
                            <xsl:text>&#160;</xsl:text>
                            <xsl:value-of select="@name"/>
                            <xsl:text>&#160;&#160;&#160;</xsl:text>
                        </xsl:for-each>
                    </fo:block>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>

    <!-- Generate github url fo:basic-link -->
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

    <!-- Generate SVG circle with language color -->
    <xsl:template name="languageColor">
        <xsl:param name="colorHex"/>
        
        <xsl:variable name="hex" select="translate(translate($colorHex, '#', ''), 'abcdef', 'ABCDEF')"/>
        <xsl:variable name="r">
            <xsl:call-template name="hexPairsToDecimal">
                <xsl:with-param name="pair" select="1"/>
                <xsl:with-param name="hex" select="$hex"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="g">
            <xsl:call-template name="hexPairsToDecimal">
                <xsl:with-param name="pair" select="2"/>
                <xsl:with-param name="hex" select="$hex"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="b">
            <xsl:call-template name="hexPairsToDecimal">
                <xsl:with-param name="pair" select="3"/>
                <xsl:with-param name="hex" select="$hex"/>
            </xsl:call-template>
        </xsl:variable>
        
        <fo:instream-foreign-object>
            <svg xmlns="http://www.w3.org/2000/svg" height="9" width="6" viewport="0 0 0 3">
                <circle cx="3" cy="5" r="3" fill="rgb({$r},{$g},{$b})"/>
            </svg>
        </fo:instream-foreign-object>
    </xsl:template>

    <!-- convert a pair of hex characters to decimal -->
    <!-- https://www.getsymphony.com/download/xslt-utilities/view/111761/ -->
    <xsl:template name="hexPairsToDecimal">
        <xsl:param name="pair" select="1"/>
        <xsl:param name="hex"/>

        <xsl:variable name="hexPair" select="substring($hex, $pair * 2 - 1, 2)"/>
        <xsl:if test="$hexPair">
            <xsl:variable name="hex" select="'0123456789ABCDEF'"/>
            <xsl:value-of select="(string-length(substring-before($hex, substring($hexPair,1,1))))*16 + string-length(substring-before($hex,substring($hexPair,2,1)))"/>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>