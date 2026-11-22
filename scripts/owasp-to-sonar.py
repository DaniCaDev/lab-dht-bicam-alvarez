#!/usr/bin/env python3
"""Convierte los hallazgos de Dependency-Check en incidencias sobre pom.xml."""

import json
import sys
from pathlib import Path

SEVERITY = {
    "CRITICAL": ("BLOCKER", "BLOCKER"),
    "HIGH": ("HIGH", "CRITICAL"),
    "MEDIUM": ("MEDIUM", "MAJOR"),
    "MODERATE": ("MEDIUM", "MAJOR"),
    "LOW": ("LOW", "MINOR"),
    "INFO": ("INFO", "INFO"),
}


def convert(report):
    rules = {}
    issues = []
    for dependency in report["dependencies"]:
        package = dependency["fileName"]
        for vulnerability in dependency.get("vulnerabilities", []):
            identifier = vulnerability["name"]
            source = vulnerability.get("source", "OWASP")
            rule_id = f"{source}:{identifier}"
            impact, severity = SEVERITY.get(vulnerability.get("severity", "UNKNOWN").upper(), ("MEDIUM", "MAJOR"))
            rules[rule_id] = {
                "id": rule_id,
                "engineId": "owasp-dependency-check",
                "name": identifier,
                "description": vulnerability.get("description") or identifier,
                "cleanCodeAttribute": "TRUSTWORTHY",
                "type": "VULNERABILITY",
                "severity": severity,
                "impacts": [{"softwareQuality": "SECURITY", "severity": impact}],
            }
            issues.append({
                "ruleId": rule_id,
                "primaryLocation": {
                    "message": f"{identifier} afecta a {package} según Dependency-Check ({source}).",
                    "filePath": "pom.xml",
                },
            })
    return {"rules": list(rules.values()), "issues": issues}


def main():
    source = Path(sys.argv[1])
    target = Path(sys.argv[2])
    report = json.loads(source.read_text(encoding="utf-8"))
    target.write_text(json.dumps(convert(report), ensure_ascii=False, indent=2) + "\n", encoding="utf-8")


if __name__ == "__main__":
    main()
