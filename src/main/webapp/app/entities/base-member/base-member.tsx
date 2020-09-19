import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './base-member.reducer';
import { IBaseMember } from 'app/shared/model/base-member.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBaseMemberProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const BaseMember = (props: IBaseMemberProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { baseMemberList, match, loading } = props;
  return (
    <div>
      <h2 id="base-member-heading">
        <Translate contentKey="touchbaseApp.baseMember.home.title">Base Members</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="touchbaseApp.baseMember.home.createLabel">Create new Base Member</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {baseMemberList && baseMemberList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="touchbaseApp.baseMember.createdAt">Created At</Translate>
                </th>
                <th>
                  <Translate contentKey="touchbaseApp.baseMember.updatedAt">Updated At</Translate>
                </th>
                <th>
                  <Translate contentKey="touchbaseApp.baseMember.role">Role</Translate>
                </th>
                <th>
                  <Translate contentKey="touchbaseApp.baseMember.base">Base</Translate>
                </th>
                <th>
                  <Translate contentKey="touchbaseApp.baseMember.member">Member</Translate>
                </th>
                <th>
                  <Translate contentKey="touchbaseApp.baseMember.base">Base</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {baseMemberList.map((baseMember, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${baseMember.id}`} color="link" size="sm">
                      {baseMember.id}
                    </Button>
                  </td>
                  <td>{baseMember.createdAt ? <TextFormat type="date" value={baseMember.createdAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{baseMember.updatedAt ? <TextFormat type="date" value={baseMember.updatedAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    <Translate contentKey={`touchbaseApp.Role.${baseMember.role}`} />
                  </td>
                  <td>{baseMember.baseId ? <Link to={`base/${baseMember.baseId}`}>{baseMember.baseId}</Link> : ''}</td>
                  <td>{baseMember.memberId ? baseMember.memberId : ''}</td>
                  <td>{baseMember.baseId ? <Link to={`base/${baseMember.baseId}`}>{baseMember.baseId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${baseMember.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${baseMember.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${baseMember.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="touchbaseApp.baseMember.home.notFound">No Base Members found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ baseMember }: IRootState) => ({
  baseMemberList: baseMember.entities,
  loading: baseMember.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BaseMember);
