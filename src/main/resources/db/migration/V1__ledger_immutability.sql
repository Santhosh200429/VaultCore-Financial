
-- ================================
-- Ledger Table (IMMUTABLE)
-- ================================

CREATE TABLE IF NOT EXISTS ledger_entries (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL,
    amount NUMERIC(19,2) NOT NULL CHECK (amount > 0),
    entry_type VARCHAR(10) NOT NULL CHECK (entry_type IN ('DEBIT','CREDIT')),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- ================================
-- Immutability Trigger
-- ================================

CREATE OR REPLACE FUNCTION prevent_ledger_update()
RETURNS trigger AS $$
BEGIN
  RAISE EXCEPTION 'Ledger entries are immutable';
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS ledger_no_update ON ledger_entries;

CREATE TRIGGER ledger_no_update
BEFORE UPDATE OR DELETE ON ledger_entries
FOR EACH ROW EXECUTE FUNCTION prevent_ledger_update();

