import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IParaUserSdmSuffix, defaultValue } from 'app/shared/model/para-user-sdm-suffix.model';

export const ACTION_TYPES = {
  SEARCH_PARAUSERS: 'paraUser/SEARCH_PARAUSERS',
  FETCH_PARAUSER_LIST: 'paraUser/FETCH_PARAUSER_LIST',
  FETCH_PARAUSER: 'paraUser/FETCH_PARAUSER',
  CREATE_PARAUSER: 'paraUser/CREATE_PARAUSER',
  UPDATE_PARAUSER: 'paraUser/UPDATE_PARAUSER',
  DELETE_PARAUSER: 'paraUser/DELETE_PARAUSER',
  RESET: 'paraUser/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IParaUserSdmSuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ParaUserSdmSuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: ParaUserSdmSuffixState = initialState, action): ParaUserSdmSuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PARAUSERS):
    case REQUEST(ACTION_TYPES.FETCH_PARAUSER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARAUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PARAUSER):
    case REQUEST(ACTION_TYPES.UPDATE_PARAUSER):
    case REQUEST(ACTION_TYPES.DELETE_PARAUSER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PARAUSERS):
    case FAILURE(ACTION_TYPES.FETCH_PARAUSER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARAUSER):
    case FAILURE(ACTION_TYPES.CREATE_PARAUSER):
    case FAILURE(ACTION_TYPES.UPDATE_PARAUSER):
    case FAILURE(ACTION_TYPES.DELETE_PARAUSER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PARAUSERS):
    case SUCCESS(ACTION_TYPES.FETCH_PARAUSER_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARAUSER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARAUSER):
    case SUCCESS(ACTION_TYPES.UPDATE_PARAUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARAUSER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/para-users';
const apiSearchUrl = 'api/_search/para-users';

// Actions

export const getSearchEntities: ICrudSearchAction<IParaUserSdmSuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PARAUSERS,
  payload: axios.get<IParaUserSdmSuffix>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IParaUserSdmSuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PARAUSER_LIST,
    payload: axios.get<IParaUserSdmSuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IParaUserSdmSuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARAUSER,
    payload: axios.get<IParaUserSdmSuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IParaUserSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARAUSER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IParaUserSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARAUSER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IParaUserSdmSuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARAUSER,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
